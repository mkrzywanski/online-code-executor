import React, { useState } from 'react';
import { ToastProvider} from 'react-toast-notifications';
import CodeExecutionForm from './components/CodeExecutionForm/CodeExecutionForm'

const App = () => {


  return (
    <ToastProvider>
    <CodeExecutionForm/>
  </ToastProvider>
  );
}

export default App;