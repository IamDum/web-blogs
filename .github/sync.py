from __future__ import print_function

import sys
from github import Github

length = len(sys.argv)

if length < 3:
    print('This script requires two command line areguments\n1. Github access token\n2. Release milestone')
    sys.exit()
auth_token = sys.argv[1]

github = Github(login_or_token=auth_token, per_page=10)
github2 = Github(login_or_token="9891839289r998r98893989853894t98", per_page=10)


def get_milestones(gihubobj, repo_name):
    repo = gihubobj.get_repo(repo_name)
    milestones = repo.get_milestones()
    return repo, milestones


repo1, repo1_milestones = get_milestones(github,"IamDum/web-blogs")
repo2, repo2_milestones = get_milestones(github2,"IamDum/NationalBank")

new_milestones_in_repo1 = [value for value in repo1_milestones if value not in repo2_milestones]

for new_milestone in new_milestones_in_repo1:
    repo2.create_milestone(new_milestone.title)
