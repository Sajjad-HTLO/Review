# Review
This application is a simple Product-Review service with the below capabilities:

- Show|Hide products to be visible
- Make products commentable | Ratable to public or to buyers only.
- Permissible users can put comments|Rates to|on products.
- Admins can `Approve` or `Reject` the comments | Rates.

# API descriptions:

## Product API: 

```http
GET /api/products:
```

In this endpoint, list of current products will be displayed with the below schema:

```javascript
{
"id": 20,
"name": "p1",
"commentableToPublic": true,
"votableToPublic": true,
"comments": [],
"ratesAverage": 0,
"commentsCount": 0,
"visible": true,
"commentable": true,
"ratable": true
}
```

```http
Put /api/products/{id}
```
This endpoint will be used to change a product's state, e.g., make it visible ,...

The body payload has the following attributes:
```javascript
{
"is_visible": true,
"is_commentable": true,
"is_commentable_to_pulic": true,
"is_ratable": true,
"is_ratable_to_pulic": true
}
```



## Comment API:

```http
GET /api/comments:
```

In this endpoint, list of comments will be displayed, example:

```javascript
{
"id": 10, // Identifier of the persisted comment
"content": "The comment content", // The comment  content
"status": "PENDING",  // The comment status, possible values are `PENDING`, `VERIFIED`, `REJECTED`
"date": 1616151820000 // The EPOCH of the comment submision date
}
```

```http
Post /api/comments
```
This endpoint is used to post a new comment for a product, the payload example is represented below:

```javascript
{
"user_id": 10, // Identifier of principal user
"product_id": 20, // Identifier of product
"is_buyer": false, // Either the principal had previously bought this product
"content": "The comment content" // The comment  content
}
```

```http
Put /api/comments/{id}
```
This endpoint will be used to verify or reject a comment

The body payload has a single `decision` the attribute:
```javascript
{
"decision": "VERIFIED" // Possible values are `PENDING`, `VERIFIED`, `REJECTED`
}
```

