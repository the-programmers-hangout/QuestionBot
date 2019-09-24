# Commands

## Key
| Symbol     | Meaning                    |
| ---------- | -------------------------- |
| (Argument) | This argument is optional. |

## Utility
| Commands | Arguments | Description          |
| -------- | --------- | -------------------- |
| Help     | (Command) | Display a help menu. |

## Configure
| Commands      | Arguments              | Description                                                       |
| ------------- | ---------------------- | ----------------------------------------------------------------- |
| addanswer     | MessageID, Question    | Manually add an already existing message as a reply to a question |
| delanswer     | MessageID              | Delete an answer from a question.                                 |
| enablelogging | ChoiceArg              | Enables / Disables bot logging                                    |
| setchannel    | ChoiceArg, TextChannel | Sets the output channel for the given argument.                   |
| setprefix     | Word                   | Sets the bot prefix.                                              |
| setrole       | Role                   | Set the lowest required role to invoke commands.                  |

## Ask
| Commands | Arguments                                | Description                 |
| -------- | ---------------------------------------- | --------------------------- |
| ask      | (Separated\|Text)                        | Ask the channel a question. |
| question | ChoiceArg, Question, ((Separated\|Text)) | Edit or delete a question   |

## Answer
| Commands | Arguments      | Description                      |
| -------- | -------------- | -------------------------------- |
| answer   | Question, Text | Answer a question                |
| delete   | Question       | Delete an answer to a question   |
| edit     | Question, Text | Edit an answer to a question     |
| list     | Question       | List answers to a given question |

