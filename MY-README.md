# Project Notes — GitHub Copilot Pro (25Mar-xxMar2026)

Summary of actions and useful links related to GitHub Copilot installation and working in Eclipse.


## Versions used
- Github-Copilot plugin :0.15.0.202601270459
- Eclipse: 2024-12 -> 4.34
- Agent model: GPT-5 mini
- Apache Tomcat: 10.0.11
- Github CLI: 2.88.0 (2026-03-10)

## References
- Installing the GitHub Copilot extension in your environment:
  https://docs.github.com/en/copilot/how-tos/set-up/install-copilot-extension?tool=eclipse
- Concepts for GitHub Copilot:
  https://docs.github.com/en/copilot/concepts-
- Using Chat variables, Chat participants & Chat commands: 	https://docs.github.com/en/copilot/reference/chat-cheat-sheet?tool=vscode#chat-variables
- Basic Github Copilot-training: https://github.com/naveennaik/Copilot-training/blob/main/labs/Lab-1-Getting-Started.md
- GitHub CLI command list: https://cli.github.com/manual/gh

## Github Copilot-Pro test scope
- Chat mode: This mode only explains and does not edit files
- Agent mode: This mode plans and prepares the change elaboratively and asks your approval to make the changes
- Plan mode: Not started
- Configure Agents: Created JavaCodeGuide.agent.md and generated best practice recommendations
- MCP Servers : GitHub


## Agent mode: Manual steps & prompts using 

- Manual: Created the Boot project from "https://start.spring.io/".Added dependencies for H2, 
Webmvc, jpa, thymeleaf

- Manual: Created a local repository inside project folder with master as initial branch, git init -b master
- Manual: Added the files, git add --all
- Manual: Committed the files, git commit -m "initial commit"
- Manual: Open MCPRegistry, search for `github-mcp-server` and install it and authenticate MCPServerPlugin to access Github.
- Manual: Install Github CLI from Url `https://cli.github.com/`. Login to github via CLI using command `gh auth login`.Once CLI is authorized in github, Github-Mcp-Server automatically gets authorized to perform actions on your behalf
- Prompt: Using github-mcp-server tool, create a new public repository as `bootghcp` in Github with owner as `savita-nandi` using API endpoint Url as `https://api.github.com/user`.Ensure the new repo is empty with no commits. Set the initial branch as `master`.Add the new remote repo `bootghcp` as remote origin locally using HTTPS protocol.
- Manual: Use Github-CLI to delete repos as prompts are not authorized to delete repos
- Manual: Login to `Github-Cli` and get additional permissions to delete a remote repo using command ` gh auth refresh -h github.com -s delete_repo`
- Manual: Push the changes committed in the local to the new repository, `git push -u origin master` as MCPServer does not allow you to pass credentials in prompts. 
- Prompt: Verify the push on GitHub.


## Deploying app: 
- Running locally: Build the application and run it with command: `mvn spring-boot:run 	-Dspring-boot.run.profiles=dev`
- Running on Tomcat: Update `catalina.bat` with the line `set 		"JAVA_OPTS=	%JAVA_OPTS	% -Dspring.profiles.active=test"`
	- Copy the H2 database file to a folder of your choice and update application-test.properties with that file path to Db Url
- Running on Cloud Docker: Pending


---


