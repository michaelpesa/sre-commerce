from flask import Flask, request, jsonify

app = Flask(__name__)

# In-memory data storage for products
products = []

# Sample Product structure:
# {
#     "id": 1,
#     "name": "Product Name",
#     "description": "Product Description",
#     "price": 10.0,
#     "available": True,
#     "quantity": 100
# }

@app.route('/products', methods=['GET'])
def get_products():
    return jsonify(products)

@app.route('/products/<int:product_id>', methods=['GET'])
def get_product(product_id):
    product = next((prod for prod in products if prod['id'] == product_id), None)
    if product is None:
        return jsonify({'message': 'Product not found'}), 404
    return jsonify(product)

@app.route('/products', methods=['POST'])
def add_product():
    new_product = request.get_json()
    new_product['id'] = len(products) + 1
    products.append(new_product)
    return jsonify(new_product), 201

@app.route('/products/<int:product_id>', methods=['PUT'])
def update_product(product_id):
    product = next((prod for prod in products if prod['id'] == product_id), None)
    if product is None:
        return jsonify({'message': 'Product not found'}), 404

    updated_data = request.get_json()
    product.update(updated_data)
    return jsonify(product)

@app.route('/products/<int:product_id>', methods=['DELETE'])
def delete_product(product_id):
    global products
    products = [prod for prod in products if prod['id'] != product_id]
    return jsonify({'message': 'Product deleted'}), 200

@app.route('/healthz', methods=['GET'])
def healthz():
    return "OK", 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080, debug=True)

