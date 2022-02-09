// ^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d$
const dayFormat = /\d\d\//i; // DD/
const monthFormat = /\d\d\/\d\d\//i; // DD/MM/
const yearFormat = /\d\d\/\d\d\/\d\d/i; // DD/MM/YY
const dayOnlyFormat = /(0[1-9]|[12][0-9]|3[01])/i;
const dayMonthFormat = /(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])/i;
const dayMonthYearFormat = /(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/](19|20)/i;

export default function beforeMaskedValueChange(newState, oldState, userInput) {
  const { value } = newState;
  const { value: oldValue } = oldState;
  const oldUserInput = oldValue + userInput;
  const isValidDay =
    userInput && dayFormat.test(value) && !dayOnlyFormat.test(oldUserInput);
  const isValidMonth =
    userInput && monthFormat.test(value) && !dayMonthFormat.test(oldUserInput);
  const isValidYear =
    userInput &&
    yearFormat.test(value) &&
    !dayMonthYearFormat.test(oldUserInput);

  if (isValidDay) {
    return oldState;
  }

  if (isValidMonth) {
    return oldState;
  }

  if (isValidYear) {
    return oldState;
  }

  return newState;
}
