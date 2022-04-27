# Product Review Service
This application is a simple Product-Review service with the below capabilities:

- Show | Hide products to be visible
- Make products `commentable` | `ratable` to public or to `buyers` only.
- Permissible users can put | give `comment(s) | rate(s)` on | to products.
- Admins can `Approve` or `Reject` the (pending) comment(s) | rate(s).

# API descriptions

## Product API

```http
GET /api/products:
```

In this endpoint, list of current products will be displayed, below is a sample output for each item of the products:

```javascript
{
  "id": 20,                    // Product identifier
  "name": "p1",                // Product name
  "commentableToPublic": true, // Is product commentable by public users or just buyers can do 
  "ratableToPublic": true,     // Is product ratable by public users or just buyers can do 
  "comments": [],              // Collection of 3 last (verifie) comments
  "ratesAverage": 3.5,         // Average rate of each product
  "commentsCount": 42,         // Count of verified comments
  "visible": true,             // Either if this product should be visible on the UI
  "commentable": true,         // Either if this product should be commentable
  "ratable": true              // Either if this product should be ratable
}
```

```http
Put /api/products/{id}
```
This endpoint will be used to change a product's state, e.g., make it visible ,...

The body payload has the following attributes:
```javascript
{
  "is_visible": true,               // Make this product visible to users
  "is_commentable": true,           // Enable commenting on this product
  "is_commentable_to_pulic": true,  // Make this product commentabl to public 
  "is_ratable": false,              // Disbale rating on this product
  "is_ratable_to_pulic": false      // This product is not ratable by public
}
```

```http
POST /api/products/{id}/comments
```
post a new comment for a product, the payload example is represented below:

```javascript
{
  "user_id": 10,                      // Identifier of principal user
  "is_buyer": false,                  // Either the principal had previously bought this product
  "content": "The comment content"    // The comment  content
}
```

```http
POST /api/products/{id}/rates
```
Rate a product, the payload example is represented below:

```javascript
{
  "user_id": 10,               // Identifier of principal user
  "is_buyer": false,           // Either the principal had previously bought this product
  "rate": 5                    // The rate value, ranging from 1-5
}
```

## Comment API

```http
GET /api/comments
```

In this endpoint, list of comments will be displayed, example:

```javascript
{
  "id": 10,                            // Identifier of the persisted comment
  "content": "The comment content",    // The comment  content
  "status": "PENDING",                 // The comment status, possible values are `PENDING`, `VERIFIED`, `REJECTED`
  "date": 1616151820000                // The EPOCH of the comment submision date
}
```

```http
Put /api/comments/{id}
```
This endpoint will be used to verify or reject a comment (By an admin user)

The body payload has a single `decision` the attribute:
```javascript
{
  "decision": "VERIFIED"        // Possible values are `PENDING`, `VERIFIED`, `REJECTED`
}
```

## Rate API

```http
GET /api/rates
```
In this endpoint, list of rates will be displayed, example:

```javascript
{
  "id": 10,                            // Identifier of the persisted rate
  "rate": 3 ,                          // The rate value ranging from 1-5
  "status": "PENDING",                 // The rate status, possible values are `PENDING`, `VERIFIED`, `REJECTED`
  "date": 1616151820000                // The EPOCH of the rate submision date
}
```

```http
Put /api/rates/{id}
```
This endpoint will be used to verify or reject a user `rate` (By an admin user)

The body payload has a single `decision` the attribute:
```javascript
{
  "decision": "VERIFIED"                // Possible values are `PENDING`, `VERIFIED`, `REJECTED`
}
```

## Descriptions

To render the page in the SPA, the `Product` list endpoint (`/api/products`) will be called to get the products with 
their intrinsic attributes, .e.g, `visible` ,`commentable`, ...

In the admin panel, a call to `/api/comments` and `/api/rates` will cause to get a paged list of the comments,
The admin can further filter `comments|rates` to see `comments|rates` with special status,
by passing the `status` query param `?status=PENDING`.
