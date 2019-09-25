# Deploying to Production

These are the requirements to set up the bot to run in production.

- A bot token
- Docker

## Pulling the Image

The bot has an already built docker image. This can be pulled like this:

```bash
docker pull docker.pkg.github.com/supergrecko/questionbot/questionbot:0.1.1
```           

## Running the bot

Running the bot is also super simple.

```bash               
# Run the bot to generate config files and to start the container
docker run -e DISCORD_TOKEN=your-discord-token docker.pkg.github.com/supergrecko/questionbot/questionbot:latest      
       
# Get the container name
docker ps -a            
               
# Restart the bot to actually run it
docker restart the-container-name
```
