from flask import Flask,render_template
app = Flask(__name__)

@app.route("/")
def hello():
    return render_template('index.html', port = 7300)

if __name__ == "__main__":
    app.run(host='localhost', port='7300')
