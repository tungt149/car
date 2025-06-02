- name: Deploy multi-env containers
  uses: appleboy/ssh-action@v1
  with:
    host: ${{ secrets.SERVER_HOST }}
    username: ${{ secrets.SERVER_USER }}
    key: ${{ secrets.SERVER_SSH_KEY }}
    script: |
      docker pull tung/myapp:latest
      docker stop myapp-dev || true
      docker rm myapp-dev || true
      docker run -d --name myapp-dev -e SPRING_PROFILES_ACTIVE=dev -p 8081:8080 tung/myapp:latest

      docker stop myapp-staging || true
      docker rm myapp-staging || true
      docker run -d --name myapp-staging -e SPRING_PROFILES_ACTIVE=staging -p 8082:8080 tung/myapp:latest

      docker stop myapp-prod || true
      docker rm myapp-prod || true
      docker run -d --name myapp-prod -e SPRING_PROFILES_ACTIVE=prod -p 8080:8080 tung/myapp:latest
