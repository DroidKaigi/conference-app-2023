import * as core from '@actions/core'
import * as github from '@actions/github'

async function run(): Promise<void> {
  try {
    const token = core.getInput('github-token')
    const deploymentId = parseInt(core.getInput('deployment-id'))
    const state = core.getInput('state')
    const deploymentUrl = core.getInput('deployment-url')
    const description = core.getInput('description')

    if (!(state === 'success' || state === 'failure')) {
      throw new Error(`status should be either success or failure`)
    }

    const octokit = github.getOctokit(token)

    const {data: deploymentStatus} = await octokit.rest.repos.createDeploymentStatus(
      {
        owner: github.context.repo.owner,
        repo: github.context.repo.repo,
        deployment_id: deploymentId,
        log_url: deploymentUrl,
        target_url: deploymentUrl,
        state,
        description
      }
    )

    core.setOutput('deployment-status', JSON.stringify(deploymentStatus))
  } catch (error) {
    if(error instanceof Error) {
      core.setFailed(error.message)
    } else {
      core.setFailed('An unknown error occurred')
    }
  }
}

run()
