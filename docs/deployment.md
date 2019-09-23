# Deploying to Production

These are the requirements to set up the bot to run in production.

- A bot token
- Docker

You can set the bot and run it by running the `deploy.sh` script. This will set up Docker and run the bot.

The first run generates a file in `config/config.json`. Rerun the bot to actually start it.

The deploy script takes one argument: The bot token.

```bash  
# Assuming you are in the root directory (the one with the README.md)

./scripts/deploy.sh <your bot token here>
```
