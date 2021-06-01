import React from 'react'
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import CodeExecutionForm from './CodeExecutionForm';
import { ToastProvider } from 'react-toast-notifications';

describe('<CodeExecutionForm />', () => {
  
  beforeEach(() => {
    fetch.resetMocks()
  });
  
  it('it should mount', () => {
    render(<div><ToastProvider><CodeExecutionForm /></ToastProvider></div>);
    
    const codeExecutionForm = screen.getByTestId('CodeExecutionForm');

    expect(codeExecutionForm).toBeInTheDocument();
  });

  it('should render output when response is received', () => {

    const code = 'some code'
    const language = 'kotlin'

    const result = render(<div><ToastProvider><CodeExecutionForm /></ToastProvider></div>)
    
    const select = result.getByTestId("code-language-select");
    fireEvent.change(select, { target: { value: language } })

    const textArea = result.container.querySelector(`[id="code-text-area"]`)
    fireEvent.change(textArea, { target: { value: code } })

    const sendButton = result.getByTestId('execute-button');
    sendButton.click()

    const expectedBody = JSON.stringify({code: code, language: language.toUpperCase()})

    expect(fetch.mock.calls[0][0]).toBe("http://localhost:8080/v1/execute")
    expect(fetch.mock.calls[0][1].body).toBe(expectedBody)

  })

  it('should start file download when download button is clicked', async () => {

    const code = 'some code'
    const language = 'kotlin'

    const result = render(<div><ToastProvider><CodeExecutionForm /></ToastProvider></div>)
    
    const select = result.getByTestId("code-language-select");
    fireEvent.change(select, { target: { value: language } })

    const textArea = result.container.querySelector(`[id="code-text-area"]`)
    fireEvent.change(textArea, { target: { value: code } });

    const obj = {hello: 'world'}
    const blob = new Blob([JSON.stringify(obj, null, 2)], {type : 'application/json'});
    fetch.mockResponseOnce(new Response(blob, {status : 200}))

    const link = {
      click: jest.fn(),
      setAttribute: jest.fn((a, b) => {}),
      href : null
    };
    jest.spyOn(document, "createElement").mockImplementation(() => link);
    jest.spyOn(document.body, "appendChild").mockImplementation(()=> {});
    window.URL.createObjectURL = jest.fn().mockImplementation(arg => "url")

    const sendButton = result.getByTestId('download-button');
    sendButton.click()
    
    await waitFor(() => expect(link.setAttribute.mock.calls[0][0]).toEqual('download'));
    expect(link.href).toBeDefined();
    expect(link.setAttribute.mock.calls[0][0]).toEqual('download')
    expect(link.setAttribute.mock.calls[0][1]).toEqual('files.zip')
    expect(link.click).toHaveBeenCalledTimes(1);
    expect(document.body.appendChild).toHaveBeenCalled()

    const expectedBody = JSON.stringify({code: code, language: language.toUpperCase()})

    expect(fetch.mock.calls[0][0]).toBe("http://localhost:8080/v1/sourceCode/compressed")
    expect(fetch.mock.calls[0][1].body).toBe(expectedBody)

  })
});