import * as core from '@actions/core'
import * as github from '@actions/github'
import * as io from '@actions/io'
import * as os from 'os'
import {dirname, resolve} from 'path'
import * as fs from 'fs'

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

    const {data: zipData} = await octokit.actions.downloadArtifact({
      owner: github.context.repo.owner,
      repo: github.context.repo.repo,
      artifact_id: artifactId,
      archive_format: 'zip'
    })

    const archivePath = `${resolvedPath}.zip`
    fs.writeFileSync(`${resolvedPath}.zip`, Buffer.from(zipData as ArrayBuffer))

    core.setOutput('archive-path', archivePath)
  } catch (error) {
    core.setFailed(error.message)
  }
}

run()
