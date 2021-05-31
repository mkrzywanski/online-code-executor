import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import App from './App';

describe('<App />', () => {
  test('it should mount', () => {
    render(<App />);
    
    // const codeWindow = screen.getByText("\@mkrzywanski")

    // expect(codeWindow).toBeInTheDocument();
  });
});