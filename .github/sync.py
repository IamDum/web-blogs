from __future__ import print_function

import sys
from github import Github

length = len(sys.argv)


try:
    if True:
       vari = 1 / 0
except Exception as e:
    print("Error creating milestone: %s" % e)
    traceback.print_exc()

auth_token = sys.argv[1]


github = Github(login_or_token=auth_token, per_page=10)


def get_milestones(gihubobj, repo_name):
    repo = gihubobj.get_repo(repo_name)
    milestones = repo.get_milestones()
    print(milestones)
    return repo, milestones


repo1, repo1_milestones = get_milestones(github,"IamDum/web-blogs")
repo2, repo2_milestones = get_milestones(github,"IamDum/NationalBank")

new_milestones_in_repo1 = [value for value in repo1_milestones if value not in repo2_milestones]


print(new_milestones_in_repo1)
for new_milestone in new_milestones_in_repo1:
    repo2.create_milestone("title")
