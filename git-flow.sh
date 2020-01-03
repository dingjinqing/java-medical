#!/bin/bash

usage(){
	echo 
	echo 创建新分支: $1 create {branch} {from}
	echo 合并分支: $1 merge {branch} {to} {tagname=}
	echo 删除远程分支: $1 delete {branch}
	echo 分支管理
}


create(){
	branch=$1
	from=$2

	if [[ "$from" = "" ]]; then
		echo -e "\033[31;1m必须填写拉取来源分支\033[0m"
		exit 1
	fi

	echo git checkout -b $branch $from
	git checkout -b $branch $from

	echo git push --set-upstream origin $branch
	git push --set-upstream origin $branch
}


merge(){
	branch=$1
	to=$2
	tagname=$3
	
	if [[ "$to" = "" ]]; then
		echo -e "\033[31;1m必须填写合并到的分支\033[0m"
		exit 1
	fi

	if [[ "$tagname" = "" && "$to" = "master" ]]; then
		echo -e "\033[31;1m合并到master必须填写tagname\033[0m"
		exit 1
	fi

	echo git checkout $to
	git checkout $to

	echo git pull
	git pull

	if [ $? != 0 ]; then
		echo -e "\033[31;1m拉取分支失败\033[0m"
		exit 2
	fi

	echo git merge --no-ff $branch -m "$branch merge to $to"
	git merge --no-ff $branch -m "$branch merge to $to"

	if [ $? != 0 ]; then
		echo -e "\033[31;1m合并有冲突，请手动合并后再push\033[0m"
		exit 3
	else

		if [[ "$to" = "master" ]]; then
			echo tag -a $tagname -m "tag $tagname"
			git tag -a $tagname -m "tag $tagname"
		fi
		
		echo git push
		git push

		if [[ "$to" = "master" ]]; then
			echo git push origin --tags
			git push origin --tags
		fi
		
	fi
}

delete(){
	branch=$1

	prompt=$prompt"\n\033[31;1m请确认\033[33;5m是否删除远程分支 ${branch} \033[31;0m\033[31;1m ?[yn] \033[0m"
	echo -e $prompt
	read is_cover
	echo ""
	if [[ "$is_cover" != "y" ]]; then
		exit 3
	fi

	echo git checkout $branch
	git checkout $branch

	echo git push origin --delete $branch
	git push origin --delete $branch

	echo git checkout master
	git checkout master

	echo git branch -d $branch
	git branch -d $branch

	echo git branch -a
	git branch -a
}

if [ $# -lt 2 ]; then
	usage git-flow
	exit 1 
fi

branch=$2
case $1 in
    create) create $branch $3;;
    merge)  merge  $branch $3 $4;;
    delete) delete $branch;;
	*) usage git-flow
esac