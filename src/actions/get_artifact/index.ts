import * as core from '@actions/core'
import * as github from '@actions/github'
import * as io from '@actions/io'
import {DownloadHttpClient} from '@actions/artifact/lib/internal/download-http-client'
import * as os from 'os'
import {dirname, resolve} from 'path'

async function run(): Promise<void> {
  try {
    const token = core.getInput('github-token')
    const artifactId = parseInt(core.getInput('artifact-id'))
    const path = core.getInput('path')

    let resolvedPath
    if (path.startsWith(`~`)) {
      resolvedPath = resolve(path.replace('~', os.homedir()))
    } else {
      resolvedPath = resolve(path)
    }
    await io.mkdirP(dirname(resolvedPath))

    const octokit = github.getOctokit(token)

    const {data: artifact} = await octokit.actions.getArtifact({
      owner: github.context.repo.owner,
      repo: github.context.repo.repo,
      artifact_id: artifactId
    })

    core.setOutput('artifact', JSON.stringify(artifact))

    const httpClient = new DownloadHttpClient()
    await httpClient.downloadSingleArtifact([
      {
        sourceLocation: artifact.archive_download_url,
        targetPath: resolvedPath
      }
    ])

    core.setOutput('destination', resolvedPath)
  } catch (error) {
    core.setFailed(error.message)
  }
}

run()
