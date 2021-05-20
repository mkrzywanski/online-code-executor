import React, { lazy, Suspense } from 'react';

const LazyCodeWindow = lazy(() => import('./CodeWindow'));

const CodeWindow = props => (
  <Suspense fallback={null}>
    <LazyCodeWindow {...props} />
  </Suspense>
);

export default CodeWindow;
