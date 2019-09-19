# Commands

## Key
| Symbol     | Meaning                    |
| ---------- | -------------------------- |
| (Argument) | This argument is optional. |

## utility
| Commands | Arguments | Description         |
| -------- | --------- | ------------------- |
| help     | (Word)    | Display a help menu |

## manage
| Commands      | Arguments              | Description                                                       |
| ------------- | ---------------------- | ----------------------------------------------------------------- |
| addanswer     | MessageID, Question    | Manually add an already existing message as a reply to a question |
| delanswer     | MessageID              | Delete an answer from a question.                                 |
| enablelogging | On or Off              | Enables / Disables bot logging                                    |
| setchannel    | ChoiceArg, TextChannel | Sets the output channel for the given argument.                   |
| setprefix     | Word                   | Sets the bot prefix.                                              |
| setrole       | Role                   | Set the lowest required role to invoke commands.                  |

## ask
| Commands | Arguments                  | Description                 |
| -------- | -------------------------- | --------------------------- |
| ask      | (Separated|Text)           | Ask the channel a question. |
| delete   | Question                   | Delete a question           |
| edit     | Question, (Separated|Text) | Edit a question             |

## answer
| Commands | Arguments      | Description         |
| -------- | -------------- | ------------------- |
| reply    | Question, Text | Reply to a question |

