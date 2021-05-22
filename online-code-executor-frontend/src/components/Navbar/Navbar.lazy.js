import React, { lazy, Suspense } from 'react';

const LazyNavbar = lazy(() => import('./Navbar'));

const Navbar = props => (
  <Suspense fallback={null}>
    <LazyNavbar {...props} />
  </Suspense>
);

export default Navbar;
