import styles from './CodeWindow.module.css';
import 'bootstrap/dist/css/bootstrap.css';
import React from 'react';
import Editor from "react-simple-code-editor";
import Prism from "prismjs";
import "prismjs/components/prism-clike";
import "prismjs/components/prism-java";
import "prismjs/components/prism-kotlin";
import "prismjs/components/prism-groovy";
import "prismjs/themes/prism.css"; //Example style, you can use another

const CodeWindow = ({ code, onCodeChange, language }) => {
  return (
    <div>
      <label htmlFor="code-text-area">Example textarea</label>
      <div class="h-25">
        <div className={styles.container_editor_area}>
          <Editor
            value={code}
            onValueChange={onCodeChange}
            highlight={(code) => Prism.highlight(code, Prism.languages[language], language)}
            padding={10}
            textareaClassName={styles.container__editor}
          />
        </div>
      </div>
    </div>
  );

};

CodeWindow.propTypes = {};

CodeWindow.defaultProps = {};

export default CodeWindow;
