# web-blogs
Web Blog : To add/update/get/delete posts

# Dependencies
	Java 11
	Postgres DB (docker-compose provided in the codebase)
	Spring boot : 2.4.x
	Gradle 5.5.x
	

# 100% Test covregae
	Spring boot test - end to end API test



# API Documetation : Swagger 
	  swagger: "2.0"
	  info:
		description: "This is the definition of the API for code test as Pierce AB"
		version: "1.0.0"
		title: "Simple blog post API"
	  host: "localhost:8080"
	  basePath: "/blog-web"
	  schemes:
	  - "http"
	  paths:
		/posts:
		  get:
			tags:
			- "post"
			summary: "Get all posts"
			description: "Returns all posts"
			operationId: "getAllPosts"
			produces:
			- "application/xml"
			- "application/json"
			responses:
			  200:
				description: "successful operation"
				schema:
				  $ref: "#/definitions/Post"
		  post:
			tags:
			- "post"
			summary: "Add a new post"
			description: ""
			operationId: "addPost"
			consumes:
			- "application/json"
			- "application/xml"
			produces:
			- "application/xml"
			- "application/json"
			parameters:
			- in: "body"
			  name: "body"
			  description: "Post object that needs to be added"
			  required: true
			  schema:
				$ref: "#/definitions/Post"
			responses:
			  201: 
				description: "OK of post"
				schema:
				  $ref: "#/definitions/Post"
			  405:
				description: "Invalid input"
		  put:
			tags:
			- "post"
			summary: "Updates a post"
			description: ""
			operationId: "updatePost"
			consumes:
			- "application/json"
			- "application/xml"
			produces:
			- "application/xml"
			- "application/json"
			parameters:
			- in: "body"
			  name: "body"
			  description: "Post object that needs to be updated"
			  required: true
			  schema:
				$ref: "#/definitions/Post"
			responses:
			  201: 
				description: "OK of post"
				schema:
				  $ref: "#/definitions/Post"
			  404: 
				description: "Post not found"
			  405:
				description: "Invalid input"
		/posts/{postId}:
		  get:
			tags:
			- "post"
			summary: "Find post by ID"
			description: "Returns a single post"
			operationId: "getPostById"
			produces:
			- "application/xml"
			- "application/json"
			parameters:
			- name: "postId"
			  in: "path"
			  description: "ID of post to return"
			  required: true
			  type: "string"
			responses:
			  200:
				description: "successful operation"
				schema:
				  $ref: "#/definitions/Post"
			  204:
				description: "No content"
		  delete:
			tags:
			- "post"
			summary: "Deletes a post"
			description: ""
			operationId: "deletePost"
			parameters:
			- name: "postId"
			  in: "path"
			  description: "Post id to delete"
			  required: true
			  type: "string"
			responses:
			  200:
				description: "successful operation"
			  404:
				description: "Post not found"
	  definitions:
		Post:
		  type: "object"
		  required:
		  - "title"
		  - "content"
		  properties:
			id:
			  type: "string"
			  example: "1"
			title:
			  type: "string"
			  example: "what I did today"
			content:
			  type: "string"
			  example: "wrote a boring post"

			  '''
