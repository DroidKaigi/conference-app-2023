import * as core from '@actions/core'
import fetch from 'node-fetch'

async function run(): Promise<void> {
  try {
    const apiToken: string = core.getInput('api-token')
    const appOwnerName: string = core.getInput('app-owner-name')
    const platform: string = core.getInput('platform').toLowerCase()
    const appId: string = core.getInput('app-id')
    const distributionName: string = core.getInput('distribution-name')
    const isTestMode: boolean = core.getInput('test') === 'true'

    if (!(platform === 'android' || platform === 'ios')) {
      throw new Error(`${platform} is not allowed`)
    }

    const url = `https://deploygate.com/api/users/${appOwnerName}/platforms/${platform}/apps/${appId}/distributions/`
    const options = {
      headers: {
        Authorization: `token ${apiToken}`
      },
      body: JSON.stringify({distribution_name: distributionName}),
      method: 'DELETE'
    }

    core.debug(url)

    if (isTestMode) {
      core.setOutput('options', JSON.stringify(options))
      core.setOutput('status', 200)
      core.setOutput('response', JSON.stringify({dummy: true}))
    } else {
      const response = await fetch(
        `https://deploygate.com/api/users/${appOwnerName}/platforms/${platform}/apps/${appId}/distributions/`,
        {
          headers: {
            Authorization: `token ${apiToken}`
          },
          body: JSON.stringify({distribution_name: distributionName}),
          method: 'DELETE'
        }
      )
      const json = await response.json()

      core.setOutput('status', response.status)
      core.setOutput('response', JSON.stringify(json))
    }
  } catch (error) {
    if(error instanceof Error) {
      core.setFailed(error.message)
    } else {
      core.setFailed('An unknown error occurred')
    }
  }
}

run()
