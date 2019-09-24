# Answer Commands

These commands are invoked by the community to reply to the bounty questions.

## Table of Contents

- [Answer](#answer)
- [Delete](#delete)
- [Edit](#edit)
- [List](#list)

## Answer

Answers a bounty question. Each user can only answer a question once.

**Example**

![Example](/.github/assets/commands/answer-example-1.png)
![Example](/.github/assets/commands/answer-example-2.png)

**Syntax**

```
$answer <id> <answer>
```              

**Arguments**      

- `id`: The question id you're answering
- `answer`: The plaintext answer

## Delete

Deletes your answer for a question

**Example**
                 
![Example](/.github/assets/commands/answer-delete-example.png)

**Syntax**

```
$delete <id>
```              

**Arguments**      

- `id`: The question you want to remove your answer from

## Edit

Edits your answer to a question

**Example**

![Example](/.github/assets/commands/answer-edit-example-1.png)
![Example](/.github/assets/commands/answer-edit-example-2.png)

**Syntax**

```
$edit <question> <answer>
```              

**Arguments**      

- `id`: The question id you're editing an answer for
- `answer`: The plaintext answer

## List

Lists all the responses to a question, if any

**Example**

![Example](/.github/assets/commands/answer-list-example.png)

**Syntax**

```
$list <id>
```              

**Arguments**      

- `id`: The question id you're grabbing the answer list for
