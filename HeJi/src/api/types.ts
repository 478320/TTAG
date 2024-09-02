export interface ResType {
  data: { [key: string]: any } | null;
  errorMsg: null | string;
  msg: null | string;
  success: boolean;
  total: number | null;
}