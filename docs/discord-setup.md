# Discord Setup

These steps are needed to create a Discord Server for the bot, and add a Bot Account for it.

## Server Setup
If you don't already have one, create a Discord server for the bot to run on. Follow the [official guide](https://support.discordapp.com/hc/en-us/articles/204849977-How-do-I-create-a-server-) if needed.

## Bot Account Setup
You will need to create an App Account to use the bot in the server that was created above. 

- Navigate to the [Developers Section](https://discordapp.com/developers/applications/) of Discord and click "New Application".
- Enter a name and icon (if required) for the bot account in the "General Information" page.

    ![App Setup](/.github/assets/discord-setup/app-setup.png)

- Copy the "Client Id" (this is needed later on)
- Click on the "Bot" section in the "Settings" menu, and the "Add Bot" button on that page.
- Give the Bot an Icon and Username.

    ![Bot Setup](/.github/assets/discord-setup/bot-setup.png)

- Save the Token (this is secret and shouldn't be revealed).


## Add the bot to your server.
Now that the Bot is created, you need to add it to your server to be used.

- Go to the OAuth2 tab in the Developer Section.
- In "Scopes" check the "bot" checkbox.
- Copy the generated URL and open the link in a new tab.

    ![OAuth Setup](/.github/assets/discord-setup/oauth-setup.png)

- In the list of servers, choose the server you want the bot to be a part of.
- Click "Authorize" and the bot will be added to your server.

    ![OAuth Authorize](/.github/assets/discord-setup/oauth.png)
