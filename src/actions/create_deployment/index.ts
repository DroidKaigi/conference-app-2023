import * as core from '@actions/core'
import * as github from '@actions/github'
import {DownloadHttpClient} from '@actions/artifact/lib/internal/download-http-client'

async function run(): Promise<void> {
  try {
    const token = core.getInput('github-token')
    const ref = core.getInput('ref')
    const sha = core.getInput('sha')
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
    core.info(`found an artifact - ${artifactName}`)

    const octokit = github.getOctokit(token)

    core.info(`will be creating a new ${environment} deployment`)
    const {data: deployment} = await octokit.repos.createDeployment({
      owner: github.context.repo.owner,
      repo: github.context.repo.repo,
      ref,
      environment,
      task: 'deploygate',
      auto_merge: false,
      required_contexts: [],
      payload: {
        runId,
        ref,
        sha,
        artifactId: targetArtifact.containerId
      }
    })

    core.info(`created a new deployment (${deployment.id})`)
    core.debug(JSON.stringify(deployment))
    core.setOutput('deployment', JSON.stringify(deployment))
  } catch (error) {
    core.setFailed(error.message)
  }
}

run()
