import './App.css';
import Header from './components/Header/Header';
import Content from './components/Content/Content';
import wrapper from "./store/store";
import 'antd/dist/antd.css';
import 'antd-button-color/dist/css/style.css';

function App() {
  return (
    <div style={{backgroundColor : "#111120"}}>
      <Header />
      <Content />
    </div>
  );
}

export default wrapper.withRedux(App);
