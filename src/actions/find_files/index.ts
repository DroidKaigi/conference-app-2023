import * as core from '@actions/core'
import * as glob from '@actions/glob'

async function run(): Promise<void> {
  try {
    const pattern: string = core.getInput('pattern')

    const globber = await glob.create(pattern)
    const files = await globber.glob()

    core.setOutput('paths', JSON.stringify(files))
  } catch (error) {
    core.setFailed(error.message)
  }
}

run()
