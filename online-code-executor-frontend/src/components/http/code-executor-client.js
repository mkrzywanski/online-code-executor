export default class ExecutorApiClient {
    constructor() {
      this.api_url = "http://localhost:8080";
      this.headers = {
        "content-type": 'application/json'
      }
    }
  
    execute = (executeRequest) => {
      const body = JSON.stringify(executeRequest);
        return fetch(this.api_url + "/v1/execute", {
            method: 'POST',
            headers: this.headers,
            body: body
          })
    };

    download = (downloadRequest) => {
        return fetch(this.api_url + "/v1/sourceCode/compressed", {
            method: 'POST', // *GET, POST, PUT, DELETE, etc.
            mode: 'cors', // no-cors, *cors, same-origin
            cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
            //credentials: 'same-origin', // include, *same-origin, omit
            headers: this.headers,
            referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
            body: JSON.stringify(downloadRequest) // body data type must match "Content-Type" header
          })
    };
}