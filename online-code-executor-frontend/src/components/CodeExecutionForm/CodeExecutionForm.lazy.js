import React, { lazy, Suspense } from 'react';

const LazyCodeExecutionForm = lazy(() => import('./CodeExecutionForm'));

const CodeExecutionForm = props => (
  <Suspense fallback={null}>
    <LazyCodeExecutionForm {...props} />
  </Suspense>
);

export default CodeExecutionForm;
