import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import CodeExecutionForm from './CodeExecutionForm';

describe('<CodeExecutionForm />', () => {
  test('it should mount', () => {
    render(<CodeExecutionForm />);
    
    const codeExecutionForm = screen.getByTestId('CodeExecutionForm');

    expect(codeExecutionForm).toBeInTheDocument();
  });
});