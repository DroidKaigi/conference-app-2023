import * as process from 'process'
import * as cp from 'child_process'
import * as path from 'path'

test('test runs', () => {
  process.env['INPUT_API-TOKEN'] = 'api_token'
  process.env['INPUT_APP-OWNER-NAME'] = 'app_owner_name'
  process.env['INPUT_PLATFORM'] = 'android'
  process.env['INPUT_APP-ID'] = 'com.example.app'
  process.env['INPUT_DISTRIBUTION-NAME'] = 'distribution_name'
  process.env['INPUT_TEST'] = 'true'
  const np = process.execPath
  const ip = path.join(
    __dirname,
    '..',
    'lib',
    'actions',
    'destroy_distribution_by_name',
    'index.js'
  )
  const options: cp.ExecFileSyncOptions = {
    env: process.env
  }
  console.log(cp.execFileSync(np, [ip], options).toString())
})
