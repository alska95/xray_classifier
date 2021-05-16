import './App.css';
import Header from './components/Header/Header';
import Content from './components/Content/Content';
import wrapper from "./store/store";

function App() {
  return (
    <>
      <Header />
      <Content />
    </>
  );
}

export default wrapper.withRedux(App);
