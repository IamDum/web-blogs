from __future__ import print_function

import sys
from collections import defaultdict
from github import Github


length = len(sys.argv)

if length < 3:
    print('This script requires two command line areguments\n1. Github access token\n2. Release milestone')
    sys.exit()
auth_token = sys.argv[1]

github = Github(login_or_token=auth_token, per_page=10)
repo_tmp = github.get_repo("IamDum/web-blogs")


print(repo_tmp)


