from flask import Flask, request, jsonify
import subprocess

app = Flask(__name__)

@app.route('/diagnose', methods=['POST'])
def diagnose():
    symptoms = request.json.get('symptoms', [])
    prompt = f"Given these symptoms: {', '.join(symptoms)}, what are the most likely medical conditions and treatment advice?"

    try:
        result = subprocess.run(
            ['ollama', 'run', 'mistral'],
            input=prompt.encode(),
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE
        )
        output = result.stdout.decode()
        return jsonify({'diagnosis': output.strip()})
    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(port=5000, debug=True)