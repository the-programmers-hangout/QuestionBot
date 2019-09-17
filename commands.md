# Commands

## Key
| Symbol     | Meaning                    |
| ---------- | -------------------------- |
| (Argument) | This argument is optional. |

## core
| Commands  | Arguments           | Description                                                       |
| --------- | ------------------- | ----------------------------------------------------------------- |
| addanswer | MessageID, Question | Manually add an already existing message as a reply to a question |
| ask       | TextChannel, Text   | Ask the channel a question.                                       |
| delanswer | MessageID           | Delete an answer from a question.                                 |
| delete    | Question            | Delete a question                                                 |
| edit      | Question, Text      | Edit a question                                                   |
| reply     | Question, Text      | Reply to a question                                               |

## utility
| Commands | Arguments | Description         |
| -------- | --------- | ------------------- |
| help     | (Word)    | Display a help menu |

## config
| Commands  | Arguments | Description                                      |
| --------- | --------- | ------------------------------------------------ |
| setprefix | Word      | Sets the bot prefix.                             |
| setrole   | Role      | Set the lowest required role to invoke commands. |

