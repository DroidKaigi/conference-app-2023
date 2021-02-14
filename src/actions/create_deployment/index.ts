import * as core from '@actions/core'
import * as github from '@actions/github'
import {DownloadHttpClient} from '@actions/artifact/lib/internal/download-http-client'

async function run(): Promise<void> {
  try {
    const token = core.getInput('github-token')
    const ref = core.getInput('ref')
    const environment =
      core.getInput('environment', {required: false}) || 'pull-requests'
    const artifactName = core.getInput('artifact-name')
    const runId = process.env['GITHUB_RUN_ID']
    if (!runId) {
      throw new Error('Unable to get GITHUB_RUN_ID env variable')
    }

    const httpClient = new DownloadHttpClient()

    const artifacts = await httpClient.listArtifacts()
    if (artifacts.count === 0) {
      throw new Error(
        `Unable to find any artifacts for the associated workflow`
      )
    }

    const targetArtifact = artifacts.value.find(artifact => {
      return artifact.name === artifactName
    })
    if (!targetArtifact) {
      throw new Error(
        `Unable to find an artifact with the name: ${artifactName}`
      )
    }

    const octokit = github.getOctokit(token)

    const {data: deployment} = await octokit.repos.createDeployment({
      owner: 'DroidKaigi',
      repo: 'conference-app-2021',
      ref,
      environment,
      task: 'deploygate',
      auto_merge: false,
      payload: {
        runId,
        ref,
        artifactId: targetArtifact.containerId
      }
    })

    core.setOutput('deployment', JSON.stringify(deployment))
  } catch (error) {
    core.setFailed(error.message)
  }
}

run()
