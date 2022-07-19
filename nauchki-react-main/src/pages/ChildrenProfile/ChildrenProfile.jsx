import React, { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useParams } from "react-router-dom";
import "./childrenProfileStyles.scss";
import { selectUserChildrenData } from "../../store/userChildren/selectors";
import { setImgChildrenThunk } from "../../store/userChildren/actions";

import { Standart } from "../../components/Standart/Standart";
import { Gallery } from "../../components/Gallery/Gallery";
import { AvatarImg } from "../../components/AvatarImg/AvatarImg";
import { FormSendFile } from "../../components/FormSendFile/FormSendFile";
import { ChildrenDevelopmentTable } from "../../components/ChildrenDevelopmentTable/ChildrenDevelopmentTable";

// eslint-disable-next-line no-unused-vars
function getDate(d) {
  let days = d % 365;

  const month = Math.floor(days / 30);
  const weeks = parseInt(days / 7);
  const years = Math.floor(d / 365);

  days -= weeks * 7;

  return { years, month, weeks, days };
}

export const ChildrenProfile = () => {
  const params = useParams();
  const dispatch = useDispatch();

  const childrens = useSelector(selectUserChildrenData);

  const [children, setChildren] = useState("");
  const [childrenPhrase, setChildrenPhrase] = useState([]);
  const [childText, setChildText] = useState("");
  const [imgChildren, setImgChildren] = useState(null);

  // eslint-disable-next-line no-unused-vars
  const [avatarChildren, setAvatarChildren] = useState(null);
  // eslint-disable-next-line no-unused-vars
  const [value, setValue] = useState("");

  //Инпут у фразы ребенка
  /*   const dispatch = useDispatch();
  const phase = useSelector((state) => state.phase.phase);
 */

  // eslint-disable-next-line no-unused-vars
  const AddNewChildPhrase = (e) => {
    e.preventDefault();
    const newChildPhrase = {
      id: Date.now(),
      childText,
    };

    setChildrenPhrase([...childrenPhrase, newChildPhrase]);
    setChildText("");
  };

  // eslint-disable-next-line no-unused-vars
  const onChange = ({ target: { value } }) => {
    setValue((prev) => (/\d+/.test(Number(value)) ? value : prev));
  };

  const uploadImage = (event) => {
    event.preventDefault();
    event.target.files[0] && setImgChildren(event.target.files[0]);
  };

  const sendFile = () => {
    const dataImg = new FormData();
    dataImg.append("file", imgChildren);
    dispatch(setImgChildrenThunk(children.id, dataImg));
  };

  useEffect(() => {
    const filtered = childrens.filter((item) =>
      item.id.toString() === params.id ? item : null
    );

    setChildren(filtered[0]);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <div className="children" key={children?.id}>
      <p className="#">Личный кабинет/{children?.name}</p>

      {/* Профиль ребенка  */}

      <div className="children__profile profile">
        <div className="profile__avatar">
          <AvatarImg imgPath={children?.img_path} />
          <FormSendFile uploadImage={uploadImage} sendFile={sendFile} />
        </div>

        <div className="profile__data">
          <h3 className="profile__title">Информация</h3>
          <h4 className="profile__name">
            Имя: <span>{children?.name}</span>
          </h4>
          <h4 className="profile__gender">
            Пол: <span>{children?.gender}</span>
          </h4>
          <h4 className="profile__birth">
            Дата рождения: <span>{children?.dateOfBirth}</span>
          </h4>
          <h4 className="profile__age">
            Возраст: <span>1 год</span>
          </h4>
        </div>
      </div>

      <Standart
        standartsStages={children.standartStages?.slice(-1)[0]}
        gender={
          (children?.gender === "Муж" && "мальчика") ||
          (children?.gender === "Жен" && "девочки")
        }
      />

      {/* Фактические данные */}
      <div className="children__information information">
        <h3 className="information__title">
          Фактические сведения о развитии вашего ребенка
        </h3>
        <div className="information__wrapper">
          <div className="children__development development">
            <form className="development__form">
              <div className="development__field">
                <label
                  className="development__field-label"
                  htmlFor="childWeiht"
                >
                  Введите вес вашего ребенка в кг
                </label>
                <input
                  name="childWeiht"
                  placeholder="Пример, 10.2"
                  className="development__field-input"
                />
              </div>
              <div className="development__field">
                <label
                  className="development__field-label"
                  htmlFor="childHeight"
                >
                  Введите рост вашего ребенка в см
                </label>
                <input
                  type="number"
                  name="childHeight"
                  placeholder="Пример, 120"
                  className="development__field-input"
                />
              </div>

              <button className="development__button">Сохранить</button>
            </form>

            
            <ChildrenDevelopmentTable />

          </div>

          <div>
            <h4>Что умеет ваш ребенок в этом возрасте</h4>
            <div></div>
          </div>
          <div>
            <h4>Забавные факты, вразы и прочее</h4>
            <div></div>
          </div>
        </div>
      </div>

      <Gallery />

      {/* Статьи по возрасту ребенка */}
      <div className="stages"></div>
    </div>
  );

  //             <input onChange={updateChildrenImg} type="file" />
  //             <button
  //               // onClick={sendFile}
  //               className="personalArea__parent_button">
  //               Добавить фото
  //             </button>

  //               <div className="infoChild_blocInputHeight">
  //                 <input
  //                   {...{ value, onChange }}
  //                   className="infoChild_input"
  //                   placeholder="Рост" />
  //               </div>
  //               <div className="infoChild_blocInputWeight">
  //                 <input className="infoChild_input" placeholder="Вес" />
  //               </div>

  //       <div className="childrenSkills">

  //           <form>
  //             <InputChild
  //               value={childText}
  //               onChange={(e) => setChildText(e.target.value)}
  //               type="text"
  //               placeholder="Смешные фразы ребенка"
  //             />
  //             <ButtonChild onClick={AddNewChildPhrase}></ButtonChild>
  //           </form>
};
