import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import CodeWindow from './CodeWindow';

describe('<CodeWindow />', () => {
  test('it should mount', () => {
    render(<CodeWindow />);
    
    const codeWindow = screen.getByTestId('CodeWindow');

    expect(codeWindow).toBeInTheDocument();
  });
});