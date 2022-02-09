import React, { useCallback, useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { withRouter } from "react-router-dom";
import ChildPost from "../../components/OneChild/OneChild";
import childPlaceholder from "../../img/childCardPlaceholder.jpg";
import ButtonChild from "../../UI/ButtonChild";
import InputChild from "../../UI/InputChild";
import { useDispatch } from "react-redux";
import { getchildrenInputAC, GET_PHRASE } from "../../store/OnechildInput";
import axios from "axios";

function getDate(d) {
  let days = d % 365;
  const month = Math.floor(days / 30);
  const weeks = parseInt(days / 7);
  const years = Math.floor(d / 365);
  days -= weeks * 7;
  return {
    years,
    month,
    weeks,
    days,
  };
}

export const OneChildrenWithoutRouter = (props) => {
  const children = useSelector((state) => state.children.children);
  const [value, setValue] = useState("");

  //Инпут у фразы ребенка
  /*   const dispatch = useDispatch();
  const phase = useSelector((state) => state.phase.phase);
 */
  const [childrenPhrase, setChildrenPhrase] = useState([]);
  const [childText, setChildText] = useState("");
  const AddNewChildPhrase = (e) => {
    e.preventDefault();
    const newChildPhrase = {
      id: Date.now(),
      childText,
    };
    /* dispatch({ type: "GET_PHRASE", payload: e.target.value }); */
    setChildrenPhrase([...childrenPhrase, newChildPhrase]);
    setChildText("");
  };

  // input
  const [dates, setDates] = useState();
  const [filteredChildren, setFilteredChildren] = useState();
  const onChange = ({ target: { value } }) =>
    setValue((prev) => (/\d+/.test(Number(value)) ? value : prev));

  // падало, потому что ф-ю надо за компонент; потому что в переменной первоначально undefined(поэтому сначала создаем переменную, потом ее в стэйт пихаем)
  useEffect(() => {
    const filtered = children.filter(
      (item) => item.id.toString() === props.match.params.id
    );

    setFilteredChildren(filtered);
    const standartStages = filtered.map((t) => t.standartStages);
    setDates(standartStages.map((t) => t.map((t) => getDate(t.days))).flat());
  }, []);
  // IMG

  const [imgChildren, setImgChildren] = useState(null);
  const [avatarChildren, setAvatarChildren] = useState(null);
  console.log(children);
  const sendFile = useCallback(async () => {
    try {
      const date = new FormData();
      date.append("file", imgChildren);
      await axios
        .post(
          `https://nauchki.herokuapp.com/addchildrenimg/${filteredChildren[0].id}`,
          date,
          {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          }
        )

        .then((res) => setAvatarChildren(res.data));

      setImgChildren();
    } catch (error) {
      console.log(error);
    }
  }, [imgChildren]);

  // DELETE

  return (
    <div>
      <p className="children_PersonalAccount">
        Личный кабинет/
        {filteredChildren && filteredChildren.map((children) => children.name)}
      </p>
      <div className="children_background">
        <div className="oneChild">
          <div>
            {filteredChildren &&
              filteredChildren.map((children) => (
                <div className="oneChild_title" key={children.id}>
                  <div className="oneChild_name"> {children.name}</div>

                  <div>
                    {children.img_path ? (
                      <img
                        className="oneChild_placeholder"
                        src={`${
                          avatarChildren ||
                          children.img_path ||
                          childPlaceholder
                        }`}
                        alt="avatarChildren"
                      />
                    ) : (
                      <img
                        className="oneChild_placeholder"
                        src={`${avatarChildren || childPlaceholder}`}
                        alt="avatarChildren"
                      />
                    )}
                  </div>
                  <div className="oneChild_age"> ГОД</div>
                  <div className="oneChild_dateOfBirth">
                    Дата рождения: <br />
                    {children.dateOfBirth}{" "}
                  </div>
                  <input
                    onChange={(e) => setImgChildren(e.target.files[0])}
                    type="file"
                  />
                  <button
                    onClick={sendFile}
                    className="personalArea__parent_button"
                  >
                    Добавить фото
                  </button>
                </div>
              ))}
          </div>
          <div className="infoChild">
            <div>
              {" "}
              {filteredChildren &&
                filteredChildren.map((children) => (
                  <div className="infoChild_dataChild">
                    <ul className="infoChild_dataParam">
                      <li>Ур 3.</li>
                      <li>Ур 2.</li>
                      <li>Ур 1.</li>
                      <li>Медиана</li>
                      <li>Ур 1.</li>
                      <li>Ур 2.</li>
                      <li>Ур 3.</li>
                    </ul>
                    <ul className="infoChild_dataHight">
                      <li className="infoChild_dataParamTab">Рост</li>
                      <li className="infoChild_dataHightList backgroundInfo_plusminus3">
                        {
                          children.standartStages[
                            children.standartStages.length - 1
                          ].medianHeightMinus3
                        }
                      </li>
                      <li className="infoChild_dataHightList backgroundInfo_plusminus2">
                        {" "}
                        {
                          children.standartStages[
                            children.standartStages.length - 1
                          ].medianHeightMinus2
                        }
                      </li>
                      <li className="infoChild_dataHightList backgroundInfo_plusminus1">
                        {" "}
                        {
                          children.standartStages[
                            children.standartStages.length - 1
                          ].medianHeightMinus1
                        }
                      </li>
                      <li className="infoChild_dataHightList  backgroundInfo_cent">
                        {" "}
                        {
                          children.standartStages[
                            children.standartStages.length - 1
                          ].medianHeight
                        }
                      </li>
                      <li className="infoChild_dataHightList backgroundInfo_plusminus1">
                        {" "}
                        {
                          children.standartStages[
                            children.standartStages.length - 1
                          ].medianHeightPlus1
                        }
                      </li>
                      <li className="infoChild_dataHightList backgroundInfo_plusminus2">
                        {
                          children.standartStages[
                            children.standartStages.length - 1
                          ].medianHeightPlus2
                        }
                      </li>
                      <li className="infoChild_dataHightList backgroundInfo_plusminus3">
                        {
                          children.standartStages[
                            children.standartStages.length - 1
                          ].medianHeightPlus3
                        }
                      </li>
                    </ul>
                    <ul className="infoChild_dataWight">
                      <li className="infoChild_dataParamTab">Вес</li>
                      <li className="infoChild_dataWightList backgroundInfo_plusminus3">
                        {
                          children.standartStages[
                            children.standartStages.length - 1
                          ].medianWeightMinus3
                        }
                      </li>
                      <li className="infoChild_dataWightList backgroundInfo_plusminus2">
                        {" "}
                        {
                          children.standartStages[
                            children.standartStages.length - 1
                          ].medianWeightMinus2
                        }
                      </li>
                      <li className="infoChild_dataWightList backgroundInfo_plusminus1">
                        {" "}
                        {
                          children.standartStages[
                            children.standartStages.length - 1
                          ].medianWeightMinus1
                        }
                      </li>
                      <li className="infoChild_dataWightList backgroundInfo_cent">
                        {" "}
                        {
                          children.standartStages[
                            children.standartStages.length - 1
                          ].medianWeight
                        }
                      </li>
                      <li className="infoChild_dataWightList backgroundInfo_plusminus1">
                        {" "}
                        {
                          children.standartStages[
                            children.standartStages.length - 1
                          ].medianWeightPlus1
                        }
                      </li>
                      <li className="infoChild_dataWightList backgroundInfo_plusminus2">
                        {
                          children.standartStages[
                            children.standartStages.length - 1
                          ].medianWeightPlus2
                        }
                      </li>
                      <li className="infoChild_dataWightList backgroundInfo_plusminus3">
                        {
                          children.standartStages[
                            children.standartStages.length - 1
                          ].medianWeightPlus3
                        }
                      </li>
                    </ul>
                  </div>
                ))}
            </div>
            <div>
              <div className="infoChild_fact">Фактические данные</div>
              <div className="infoChild_faсtInfo">
                <div className="infoChild_blocInputHeight">
                  <input
                    {...{ value, onChange }}
                    className="infoChild_input"
                    placeholder="Рост"
                  ></input>
                </div>
                <div className="infoChild_blocInputWeight">
                  <input className="infoChild_input" placeholder="Вес"></input>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="childrenSkills">
          <div className="childrenSkills_info">
            <div className="childrenSkills_infoNow">
              Что должен уметь делать ребенок в этом возрасте?
            </div>
            <div className="childrenSkills_infoNowStend">
              {filteredChildren &&
                filteredChildren.map(
                  (children) =>
                    children.standartStages[children.standartStages.length - 1]
                      .skills
                )}
            </div>
          </div>
          <div className="childrenSkills_info">
            <div className="childrenSkills_infoNow">
              Что умеет ребенок в этом возрасте?
            </div>
            <div className="childrenSkills_infoNowStend">ИНФА</div>
          </div>
        </div>
        <div className="oneChildren_banner">БАННЕР С РЕКЛАМОЙ</div>
        <div className="oneChildren_back">
          <div className="oneChildren_gallery">
            Галерея <button className="circle"></button>
          </div>

          <div className="oneChildren_upImg">
            <div className="oneChildren_img"></div>
            <div className="oneChildren_img"></div>
          </div>
          <div className="oneChildren_gallery"> Смешные фразы ребенка </div>

          <div>
            <form>
              <InputChild
                value={childText}
                onChange={(e) => setChildText(e.target.value)}
                type="text"
                placeholder="Смешные фразы ребенка"
              />
              <ButtonChild onClick={AddNewChildPhrase}></ButtonChild>
            </form>
            <div childrenPhrase={childrenPhrase}>
              {childrenPhrase.map((childPh, index) => (
                <ChildPost number={index + 1} post={childPh} key={childPh.id} />
              ))}
            </div>
            <h1 className="oneChildren_paragArtic">Читать полезные статьи</h1>
          </div>
          <div className="oneChildren_articles">
            <div className="oneChildren_articlesPage"></div>
            <div className="oneChildren_articlesPage"></div>
          </div>
        </div>
        <ul className="oneChildren_list">
          {dates &&
            dates.map((t) => {
              return (
                <li className="oneChildren_listDate" key={t.id}>{`
            ${typeof t.years === "number" && t.years > 0 ? t.years + "г." : ""} 
            ${
              typeof t.months === "number" && t.months > 0
                ? t.months + "м."
                : ""
            } 
            ${
              typeof t.weeks === "number" && t.days == 0 && t.weeks > 0
                ? t.weeks + "нед."
                : ""
            } 
            ${typeof t.days === "number" && t.weeks == 0 ? t.days + "д." : ""} 
          `}</li>
              );
            })}
        </ul>
      </div>
    </div>
  );
};

export const OneChildren = withRouter(OneChildrenWithoutRouter);
