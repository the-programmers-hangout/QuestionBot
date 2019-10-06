# Deploying to Production

These are the requirements to set up the bot to run in production.

- A bot token
- Docker

## Pulling the Image

The bot has an already built docker image. This can be pulled like this:

```bash
docker pull docker.pkg.github.com/supergrecko/questionbot/questionbot:0.1.2
```           

## Running the bot

Running the bot is also super simple.

```bash               
# Run the bot to generate config files and to start the container
docker run -e DISCORD_TOKEN=your-discord-token docker.pkg.github.com/supergrecko/questionbot/questionbot:0.1.2      
       
# Get the container name
docker ps -a            
               
# Restart the bot to actually run it
docker restart the-container-name
```

## Next Steps

After you've got the bot running you want to set up the logging and output channels. This is done via the `$setchannel` command.

Details can be found [here.](/docs/command-manage.md#setchannel-admin-only)
