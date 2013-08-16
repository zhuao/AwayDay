package com.thoughtworks.mobile.awayday.utils;

import android.content.Context;

public enum ActionStatus {
    SESSION_CONFLICT,
    SUCCESS,
    SHARE_WITH_SERVER_ERROR,
    JOIN_SUCCESS,
    UNJOIN_SUCCESS,
    SAVE_TO_LOCAL_ERROR,
    SHARE_SUCCESS,
    SAVE_SUCCESS_BUT_SHARE_ERROR,
    EMPTY;


//  static
//  {
    // Byte code:
    //   0: new 21	com/thoughtworks/mobile/awayday/utils/ActionStatus$1
    //   3: dup
    //   4: ldc 22
    //   6: iconst_0
    //   7: invokespecial 26	com/thoughtworks/mobile/awayday/utils/ActionStatus$1:<init>	(Ljava/lang/String;I)V
    //   10: putstatic 28	com/thoughtworks/mobile/awayday/utils/ActionStatus:SESSION_CONFLICT	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   13: new 2	com/thoughtworks/mobile/awayday/utils/ActionStatus
    //   16: dup
    //   17: ldc 29
    //   19: iconst_1
    //   20: invokespecial 30	com/thoughtworks/mobile/awayday/utils/ActionStatus:<init>	(Ljava/lang/String;I)V
    //   23: putstatic 32	com/thoughtworks/mobile/awayday/utils/ActionStatus:SUCCESS	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   26: new 34	com/thoughtworks/mobile/awayday/utils/ActionStatus$2
    //   29: dup
    //   30: ldc 35
    //   32: iconst_2
    //   33: invokespecial 36	com/thoughtworks/mobile/awayday/utils/ActionStatus$2:<init>	(Ljava/lang/String;I)V
    //   36: putstatic 38	com/thoughtworks/mobile/awayday/utils/ActionStatus:SHARE_WITH_SERVER_ERROR	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   39: new 40	com/thoughtworks/mobile/awayday/utils/ActionStatus$3
    //   42: dup
    //   43: ldc 41
    //   45: iconst_3
    //   46: invokespecial 42	com/thoughtworks/mobile/awayday/utils/ActionStatus$3:<init>	(Ljava/lang/String;I)V
    //   49: putstatic 44	com/thoughtworks/mobile/awayday/utils/ActionStatus:JOIN_SUCCESS	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   52: new 46	com/thoughtworks/mobile/awayday/utils/ActionStatus$4
    //   55: dup
    //   56: ldc 47
    //   58: iconst_4
    //   59: invokespecial 48	com/thoughtworks/mobile/awayday/utils/ActionStatus$4:<init>	(Ljava/lang/String;I)V
    //   62: putstatic 50	com/thoughtworks/mobile/awayday/utils/ActionStatus:UNJOIN_SUCCESS	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   65: new 52	com/thoughtworks/mobile/awayday/utils/ActionStatus$5
    //   68: dup
    //   69: ldc 53
    //   71: iconst_5
    //   72: invokespecial 54	com/thoughtworks/mobile/awayday/utils/ActionStatus$5:<init>	(Ljava/lang/String;I)V
    //   75: putstatic 56	com/thoughtworks/mobile/awayday/utils/ActionStatus:SAVE_TO_LOCAL_ERROR	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   78: new 58	com/thoughtworks/mobile/awayday/utils/ActionStatus$6
    //   81: dup
    //   82: ldc 59
    //   84: bipush 6
    //   86: invokespecial 60	com/thoughtworks/mobile/awayday/utils/ActionStatus$6:<init>	(Ljava/lang/String;I)V
    //   89: putstatic 62	com/thoughtworks/mobile/awayday/utils/ActionStatus:SHARE_SUCCESS	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   92: new 64	com/thoughtworks/mobile/awayday/utils/ActionStatus$7
    //   95: dup
    //   96: ldc 65
    //   98: bipush 7
    //   100: invokespecial 66	com/thoughtworks/mobile/awayday/utils/ActionStatus$7:<init>	(Ljava/lang/String;I)V
    //   103: putstatic 68	com/thoughtworks/mobile/awayday/utils/ActionStatus:SAVE_SUCCESS_BUT_SHARE_ERROR	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   106: new 2	com/thoughtworks/mobile/awayday/utils/ActionStatus
    //   109: dup
    //   110: ldc 69
    //   112: bipush 8
    //   114: invokespecial 30	com/thoughtworks/mobile/awayday/utils/ActionStatus:<init>	(Ljava/lang/String;I)V
    //   117: putstatic 71	com/thoughtworks/mobile/awayday/utils/ActionStatus:EMPTY	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   120: bipush 9
    //   122: anewarray 2	com/thoughtworks/mobile/awayday/utils/ActionStatus
    //   125: astore_0
    //   126: aload_0
    //   127: iconst_0
    //   128: getstatic 28	com/thoughtworks/mobile/awayday/utils/ActionStatus:SESSION_CONFLICT	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   131: aastore
    //   132: aload_0
    //   133: iconst_1
    //   134: getstatic 32	com/thoughtworks/mobile/awayday/utils/ActionStatus:SUCCESS	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   137: aastore
    //   138: aload_0
    //   139: iconst_2
    //   140: getstatic 38	com/thoughtworks/mobile/awayday/utils/ActionStatus:SHARE_WITH_SERVER_ERROR	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   143: aastore
    //   144: aload_0
    //   145: iconst_3
    //   146: getstatic 44	com/thoughtworks/mobile/awayday/utils/ActionStatus:JOIN_SUCCESS	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   149: aastore
    //   150: aload_0
    //   151: iconst_4
    //   152: getstatic 50	com/thoughtworks/mobile/awayday/utils/ActionStatus:UNJOIN_SUCCESS	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   155: aastore
    //   156: aload_0
    //   157: iconst_5
    //   158: getstatic 56	com/thoughtworks/mobile/awayday/utils/ActionStatus:SAVE_TO_LOCAL_ERROR	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   161: aastore
    //   162: aload_0
    //   163: bipush 6
    //   165: getstatic 62	com/thoughtworks/mobile/awayday/utils/ActionStatus:SHARE_SUCCESS	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   168: aastore
    //   169: aload_0
    //   170: bipush 7
    //   172: getstatic 68	com/thoughtworks/mobile/awayday/utils/ActionStatus:SAVE_SUCCESS_BUT_SHARE_ERROR	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   175: aastore
    //   176: aload_0
    //   177: bipush 8
    //   179: getstatic 71	com/thoughtworks/mobile/awayday/utils/ActionStatus:EMPTY	Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   182: aastore
    //   183: aload_0
    //   184: putstatic 73	com/thoughtworks/mobile/awayday/utils/ActionStatus:$VALUES	[Lcom/thoughtworks/mobile/awayday/utils/ActionStatus;
    //   187: return
//  }

    public void showMessage(Context paramContext) {
    }
}

/* Location:           /Users/zhuao/repository/awayday/decompiler/AwayDay/classes-dex2jar.jar
 * Qualified Name:     com.thoughtworks.mobile.awayday.utils.ActionStatus
 * JD-Core Version:    0.6.2
 */
