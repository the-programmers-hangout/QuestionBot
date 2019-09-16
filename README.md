# QuestionBot

QuestionBot is a Discord bot designed to create questions for the community to answer.

The command list for QuestionBot can be found [here](commands.md).

## Setup

This section shows you the steps to setting up and running QuestionBot.

### Production

These are the requirements to set up the bot to run in production.

- A bot token
- Docker

You can set the bot and run it by running the `deploy.sh` script. This will set up Docker and run the bot.

The deploy script takes one argument: The bot token.

```bash
./deploy.sh <your bot token here>
```

### Development

These are the requirements to develop the bot.

- Git (to clone, can also download .zip)
- JDK version 8+
- IntelliJ IDEA
    - If you are not using IntelliJ IDEA you will need Maven as well.

To create a run config for development you want to use `com.supergrecko.questionbot.QuestionBotKt` as the main file.

There should be a single program argument which is the Discord Bot Token.

![Example Run Config](.github/example-run-config.png)

## License

QuestionBot is licensed under the MIT License.
