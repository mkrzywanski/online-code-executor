import CodeWindow from '../CodeWindow/CodeWindow';
import React, { useState } from 'react';
import ExecutorApiClient from '../http/code-executor-client'
import { useToasts } from 'react-toast-notifications';


const CodeExecutionForm = () => {
  const client = new ExecutorApiClient();

  const [code, setCode] = useState('');
  const [language, setLanguage] = useState('java');
  const [executionResult, setExecutionResult] = useState('');
  const { addToast } = useToasts();

  const handleCodeChange = code => setCode(code);
  const handleLanguageChange = event => setLanguage(event.target.value)

  const execute = (event) => {
    event.preventDefault()
    let request = {
      code: code,
      language: language.toUpperCase()
    }
    client.execute(request)
      .then(res => {
        if (res.status === 200) {
          return res.json()
        } else {
          return res.json().then(error => Promise.reject(error))
        }
      })
      .then(json => {
        setExecutionResult(json.result)
      })
      .catch(err => addToast(err.message, { appearance: 'error' }))
  };

  const download = async (event) => {
    event.preventDefault()
    let request = {
      code: code,
      language: language.toUpperCase()
    }
    await client.download(request)
      .then(async response => {
        if (response.status === 200) {
          return response.blob()
        } else {
          return response.json().then(error => Promise.reject(error));
        }
      })
      .then(blob => {
        const link = document.createElement('a');
        const href = window.URL.createObjectURL(blob);
        link.href = href;
        link.setAttribute('download', 'files.zip');
        document.body.appendChild(link);
        link.click();
      })
      .catch(error => addToast(error.message, { appearance: 'error' }))
  }

  return (
    <div className="CodeExecutionForm" data-testid="CodeExecutionForm">
      <div className="container-fluid">
        <form>
          <CodeWindow data-testid="code-window" code={code} onCodeChange={handleCodeChange} language={language} />
          <div class="row mb-3">
            <div class="col-md-9">
              <div className="form-group" data-testid="CodeWindow">
                <label htmlFor="code-output-area">Output</label>
                <textarea className="form-control" id="code-output-text-area" rows="3" value={executionResult} readOnly />
              </div>
            </div>
            <div class="col-md-3">
              <div class="row">
                <div className="form-group">
                  <label htmlFor="language-select">Language</label>
                  <select className="form-control" value={language} onChange={handleLanguageChange} id="code-language-select" data-testid="code-language-select">
                    <option value="java">Java</option>
                    <option value="kotlin">Kotlin</option>
                    <option value="groovy">Groovy</option>
                  </select>
                </div>
              </div>
              <div class="row">
              <div class="mt-2">
                <button id="execute-button" data-testid="execute-button" type="submit" className="btn btn-primary mr-1" onClick={execute}>
                  Execute
                </button>
                &nbsp;
                <button id="download-button" data-testid="download-button" className="btn btn-primary ml-1" onClick={download}>
                  Download
                </button>
              </div>
            </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}

export default CodeExecutionForm;
