import * as process from 'process'
import * as cp from 'child_process'
import * as path from 'path'

test('test runs', () => {
  process.env['INPUT_PATTERN'] = 'actions/**/*.yml'
  const np = process.execPath
  const ip = path.join(
    __dirname,
    '..',
    'lib',
    'actions',
    'find_files',
    'index.js'
  )
  const options: cp.ExecFileSyncOptions = {
    env: process.env
  }
  console.log(cp.execFileSync(np, [ip], options).toString())
})
