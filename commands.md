# Commands

## Key
| Symbol     | Meaning                    |
| ---------- | -------------------------- |
| (Argument) | This argument is optional. |

## Ask
| Commands | Arguments                                | Description                 |
| -------- | ---------------------------------------- | --------------------------- |
| Question | ChoiceArg, Question, ((Separated\|Text)) | Edit or delete a question   |
| ask      | (Separated\|Text)                        | Ask the channel a question. |

## Answer
| Commands | Arguments      | Description                      |
| -------- | -------------- | -------------------------------- |
| Answer   | Question, Text | Answer a question                |
| Delete   | Question       | Delete an answer to a question   |
| Edit     | Question, Text | Edit an answer to a question     |
| List     | Question       | List answers to a given question |

## Configure
| Commands      | Arguments              | Description                                                       |
| ------------- | ---------------------- | ----------------------------------------------------------------- |
| AddAnswer     | Question, User, Text   | Manually add an already existing message as a reply to a question |
| DelAnswer     | Question, User         | Delete an answer from a question.                                 |
| EnableLogging | ChoiceArg              | Enables / Disables bot logging                                    |
| SetChannel    | ChoiceArg, TextChannel | Sets the output channel for the given argument.                   |
| SetPrefix     | Word                   | Sets the bot prefix.                                              |
| SetRole       | Role                   | Set the lowest required role to invoke commands.                  |

## Utility
| Commands | Arguments | Description                                 |
| -------- | --------- | ------------------------------------------- |
| Help     | (Command) | Display a help menu.                        |
| Ping     | <none>    | Display the network ping of the bot.        |
| Uptime   | <none>    | Displays how long the bot has been running. |

