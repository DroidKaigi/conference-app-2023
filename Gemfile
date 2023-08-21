# frozen_string_literal: true

source "https://rubygems.org"

repo_root = `git rev-parse --show-toplevel`.chop
File.open("#{repo_root}/.ruby-version", "r") do |f|
    ruby file: f
end

gem 'fastlane', '2.214.0'
gem 'danger', '9.3.0'
gem 'danger-swiftlint', '0.33.0'
gem 'danger-xcode_summary', '1.2.0'
gem 'danger-xcov', '0.5.0'
gem 'xcpretty-json-formatter', '0.1.1'
