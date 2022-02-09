import { combineReducers, createStore, applyMiddleware } from "redux";
import { compose } from "redux";
import { postsReducer } from "./postsReducer";
import { userReducer } from "./userReducer";
import { childrenInputReducer } from "./OnechildInput";
import storage from "redux-persist/lib/storage"; // localStorage
import { persistStore, persistReducer } from "redux-persist";
import { childrenReducer } from "./childrenReducer";
import thunk from "redux-thunk";

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

// создаем объект конфигурации для persist
const persistConfig = {
  key: "root",
  storage,
};
const rootReducer = combineReducers({
  user: userReducer,
  posts: postsReducer,
  children: childrenReducer,
  phase: childrenInputReducer,
});

// оборачиваем редьюсеры в persist
const persistedReducer = persistReducer(persistConfig, rootReducer);

export const store = createStore(
  persistedReducer,
  composeEnhancers(applyMiddleware(thunk))
);

// создаем persistor
export const persistor = persistStore(store);
