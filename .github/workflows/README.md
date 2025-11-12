# GameFlix CI/CD

This is a step by step instructions for the CI/CD setup for springboot/mysql project.
The workflow ensures push or pull request to the main branch triggers an automated build and Docker image creation.


## Create .github/workflow/ci.yml

    In the project root, create the following path if it does not exist:
    .github/workflow/ci.yml

## Create dockerfile

    In your project root, create a file named Dockerfile.

## Verify build locally

    Run Maven locally to confirm the project builds:
    
    Then build and run the Docker image:
## Push and commit to Github

    Commit and push your changes:
    
    .github/workflows/ci.yml
    
    Dockerfile
    
    Your source code
    
    This should trigger the Actions workflow automatically on the main branch.

## Monitor workflow run

    Go to your GitHub repository Actions tab.
    
    Confirm the workflow runs successfully:
        -Checkout
        -JDK setup
        -Maven build
        -Docker build
