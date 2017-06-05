---
title: Git Command
layout: post
---
# Git 자주 사용하는 명령어
---------------------------------------
Git 에서 자주 사용하는 명령어를 정리.

### 환경 설정
    > git config --global user.name "사용자명" 
    사용자를 등록하는 명령어(필수)
    > git config --global user.email "이메일주소" 
    이메일 주소를 등록하는 명령어(필수)
    > git config --global --list 
    현재 설정정보를 조회할 수 있습니다. 

    --global은 전역설정에 대한 옵션이며, 현재 저장소만 적용할 때는 부여하지 않음.
     
### 기본적인 명령어
	> git --version
    현재 Git의 버전을 조회.

	> git init
	현재 디렉토리에 Git 저장소를 생성.

	> git add [-i,-p] 파일명
	> git add --all
	untracked files의 파일들을 Git에서 추적하거나, 파일은 생성/변경 했지만 반영이 되지 않은 파일들을 스테이징 영역에 추가.
    -i 옵션을 설정하여 명령어 실행 시 대화형모드가 시작되며 파일의 일부만 선택해서 스테이징 영역에 추가.
    -p 옵션을 사용하여 명령어 실행 시 -i 대화형모드없이 바로 패치모드를 사용이 가능.
    --all 옵션을 사용하여 명령어 실행 시 생성/변경된 모든 파일을 스테이징 영역에 추가.

	> git commit -m "커밋메시지"
	스테이징 영역에 올라가 있는 파일들을 커밋. 
	-m 옵션은 커밋메시지를 주는 옵션으로 여러 줄의 커밋메시지를 쓸 경우 -m 을 여러 개 사용. 
	-a 옵션을 사용하면 스테이징에 올리는 작업(add 명령어)과 커밋이 동시에 실행.
	-m 옵션을 사용하지 않을때 -v 옵션을 사용하면 편집기에 커밋하려는 변경사항의 다른점을 보여줌. 
	특정파일만 커밋하려면 마지막에 파일명을 추가하면 됨.

	> git commit -C HEAD -a --amend
	지정한 커밋의 로그메시지를 재사용하여 기존에 실행된 커밋을 수정. 
	-c를 사용하면 기존메시지를 수정할 수 있는 편집기가 실행.

	> git status
	커밋되지 않은 변경사항을 조회.

	> git diff
	스테이징영역과 현재 작업트리의 차이점을 보여줌. 
	--cached 옵션을 추가하면 스테이징영역과 저장소의 차이점을 볼 수 있다. git diff HEAD 를 입력하여 명령어를 실행하면 저장소, 스테이징영역, 작업트리의 차이점을 볼 수 있다. 
	파라미터로 log와 동일하게 범위를 지정할 수 있으며, --stat 옵션을 추가하면 변경사항에 대한 통계를 볼 수 있습니다.

	> git mv 기존파일명 새로운파일명
	저장소에 존재하는 기존 파일명을 새로운파일명으로 변경해주며, 변경이력은 그대로 유지.

	> git checkout -- 파일명
	아직 스테이징이나 커밋을 하지 않은 파일의 변경내용을 취소하고 이전 커밋상태로 변경하는 명령어이며, svn에서 revert와 동일.     

### Branch와 Tag에 사용되는 명령어
	> git branch
	현재 존재하는 브랜치를 조회.
    -r 옵션을 사용하여 명령어 실행 시 원격 저장소의 브랜치를 확인.

	> git branch 브랜치명
	브랜치를 생성(체크아웃은 하지 않음)

	> git branch -d 브랜치명
	브랜치를 삭제.

	> git branch -m 기존브랜치명 새로운브랜치명
	존재하는 브랜치를 새로운브랜치로 변경합니다. 
	이미 존재하는 브랜치명이 있을 경우에는 에러가 발생하므로 -M 옵션을 사용하면 존재하고 있는 브랜치의 경우에도 덮어씁니다.

	> git tag 태그명 브랜치명
	브랜치명의 현재시점에 태그명으로 된 태그를 붙이며, git tag만 입력하면 현재 존재하는 태그 목록을 조회.

	> git checkout 브랜치명/태그명
	해당 브랜치나 태그로 작업트리를 변경. 

	> git checkout -b 브랜치명B 브랜치명A
	브랜치명A에서 브랜치명B라는 새로운 브랜치를 만들면서 체크아웃을 합니다.

	> git rebase 브랜치명
	브랜치명의 변경사항을 현재 브랜치에 적용.

	> git merge 브랜치명
	브랜치명의 브랜치를 현재 브랜치로 병합. 
	--squash 옵션을 주면 브랜치명의 모든 커밋을 하나의 커밋으로 생성.

	> git cherry-pick 커밋명
	커밋명의 특정 커밋만을 선택해서 현재 브랜치에 커밋으로 생성. 
	-n 옵션을 주면 작업트리에 합치지만 커밋은 하지 않기 때문에 여러개의 커밋을 합쳐서 커밋이 가능.	

### 로그관리에 사용되는 명령어
	> git log
	커밋된 로그들을 조회하며, -1나 -2같은 옵션을 주어 출력할 커밋된 로그의 갯수를 지정 가능함. 
	--pretty=oneline 옵션을 주면 한줄로 보여주고, --pretty=format:"%h %s"처럼 형식을 지정.
	-p 옵션을 사용하면 변경된 내용을 동시에 조회. 
	--since="5 hours" 이나 --before="5 hours"같은 옵션도 사용이 가능함. --graph 옵션을 주면 브랜치 트리를 볼 수 있습니다.

	> git log 커밋명
	해당 커밋명의 로그를 볼 수 있습니다. 커밋명A..커밋명B (마침표2개)와 같이 입력하면 커밋명A이후부터 커밋명B까지의 로그를 조회. 
	^은 -1과 동일하므로 HEAD^라고 하면 바로 이전 커밋을 의미하며, HEAD^^^와 같이 쓸 수 있으며, HEAD~3을 하면 HEAD의 3개 이전의 커밋을 의미

	> git blame 파일명
	커밋명과 커밋한 사람 등의 정보를 조회

	> git blame -L 10,15 파일명
	-L 옵션을 사용하면 10줄부터 15줄로 범위를 지정해서 볼수 있고 15대신 +5와 같이 사용할 수 있으며, 숫자의 범위 대신 정규식도 사용 가능.

	> git blame -M 파일명
	-M 옵션을 사용하면 반복되는 패턴을 찾아서 복사하거나 이동된 내용을 탐색.  
	-C 옵션을 사용하면 파일간의 복사한 경우를 탐색. 
	-C 옵션은 git log에서도 사용가능 하며 내용의 복사를 찾을 때는, git log에서 -p 옵션을 사용.

	> git revert 커밋명
	기존의 커밋에서 변경한 내용을 취소해서 새로운 커밋을 생성. 
	-n 옵션을 사용하면 바로 커밋하지 않기 때문에 revert를 여러번한 다음에 커밋.(항상 최신의 커밋부터 Revert 를 해야함)

	> git reset 커밋명
	이전 커밋을 수정하기 위해서 사용.
	--soft 옵션을 사용하면 이전 커밋을 스테이징하고 커밋은 하지 않음
	--hard옵션은 저장소와 작업트리에서 커밋을 제거할 수 있으며, git reset HEAD 와 같이 입력하면 최근 1개의 커밋을 취소.

	> git rebase -i 커밋범위
	-i옵션으로 대화형모드로 커밋 순서를 변경하거나 합치는 등의 작업을 수행.

### 원격저장소
	> git clone 저장소주소 폴더명
	원격저장소를 복제하여 저장소를 생성함. 폴더명은 생략이 가능.

	> git fetch
	원격저장소의 변경사항 가져와서 원격브랜치를 갱신.
	 
	> git pull
	git fetch에서 하는 원격저장소의 변경사항을 가져와서 지역브랙치에 합치는 작업을 한번에 수행. 파라미터로 풀링할 원격저장소와 반영할 지역브랜치의 부여가 가능하다.

	> git push
	파라미터를 주지 않으면 origin 저장소에 Push 하며, 현재 지역브랜치와 같은 이름의 브랜치에 Push 합니다. 
	--dry-run 옵션을 사용하면 푸싱된 변경사항을 조회 가능. 
	로컬에서 tag를 달았을 경우에 기본적으로 Push 하지 않으므로, git push origin 태그명이나 모든 태그를 올리기 위해서 git push origin --tags를 사용.

	> git remote add 이름 저장소주소
	새로운 원격 저장소를 추가.

	> git remote
	추가한 원격저장소의 목록을 확인.

	> git remote show 이름
	해당 원격저장소의 정보를 조회.

	> git remote rm 이름
	원격저장소를 제거.


### 서브모듈
	> git submodule
	연관된 하위모듈을 확인.

	> git submodule add 저장소주소 서브모듈경로
	새로운 하위모듈을 해당경로에 추가하며, 초기화 되지는 않으므로 커밋 해쉬 앞에 '-' 로 표시됨.

	> git submodule init 서브모듈경로
	서브모듈을 초기화.

	> git submodule update 서브모듈경로
	서브모듈의 변경사항을 적용.(저장소의 최신커밋을 추적하지 않음)


### 기타 명령어
	> git archive --format=tar --prefix=폴더명/ 브랜치혹은태그 | gzip > 파일명.tar.gz
	> git archive --format=zip --prefix=폴더명/ 브랜치혹은태그 > 파일명.zip
	해당 브랜치나 태그를 압축파일로 생성. --prefix를 주면 압축파일이 해당 폴더 안에 생성되도록 설정이 가능.

	> git mergetool
	설정에 merge.tool의 값에 있는 Merge Tool을 찾아 실행.

	> git gc
	저장소의 로그 저장 방식을 최적화. 
	--aggressive 옵션을 주면 더 자세하게 최적화 작업이 실행.

	> git rev-parse --show-toplevel
	git 저장소내에서 입력하면 루트디렉토리를 조회.	