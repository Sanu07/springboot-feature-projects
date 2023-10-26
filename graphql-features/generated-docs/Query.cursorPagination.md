# Query.cursorPagination: UserConnection
                 
## Arguments
| Name | Description | Required | Type |
| :--- | :---------- | :------: | :--: |
| first |  | Optional | Int |
| after |  | Optional | String |
| last |  | Optional | Int |
| before |  | Optional | String |
            
## Example
```graphql
{
  cursorPagination(first: 123456789, after: "randomString", last: 123456789, before: "randomString") {
    edges
    pageInfo
  }
}

```