import * as core from '@actions/core'
import FormData from 'form-data';
import fetch from 'node-fetch'

async function run(): Promise<void> {
  try {
    const apiToken: string = core.getInput('api-token')
    const appOwnerName: string = core.getInput('app-owner-name')
    const platform: string = core.getInput('platform').toLowerCase()
    const appId: string = core.getInput('app-id')
    const distributionName: string = core.getInput('distribution-name')

    if (!(platform === 'android' || platform === 'ios')) {
      throw new Error(`${platform} is not allowed`)
    }

    const form = new FormData()

    form.append('distribution_name', distributionName)

    const url = `https://deploygate.com/api/users/${appOwnerName}/platforms/${platform}/apps/${appId}/distributions/`
    const options = {
      headers: {
        Authorization: `token ${apiToken}`
      },
      body: form,
      method: 'DELETE'
    }
    const response = await fetch(
      url,
      options,
    )
    const json = await response.json()

    core.setOutput('status', response.status)
    core.setOutput('response', JSON.stringify(json))
  } catch (error) {
    if(error instanceof Error) {
      core.setFailed(error.message)
    } else {
      core.setFailed('An unknown error occurred')
    }
  }
}

run()
