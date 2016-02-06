/*
 * Copyright 2016 Hippo Seven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hippo.ehviewer;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.text.TextUtils;
import android.widget.Toast;

import com.hippo.ehviewer.client.EhUrlOpener;
import com.hippo.util.CustomTabsHelper;

public final class UrlOpener {

    private UrlOpener() {
    }

    public static void openUrl(Activity activity, String url, boolean ehUrl, boolean customTabs) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        Intent intent;
        Uri uri = Uri.parse(url);

        if (ehUrl && EhUrlOpener.openUrl(activity, url)) {
            return;
        }

        // CustomTabs
        if (customTabs) {
            String packageName = CustomTabsHelper.getPackageNameToUseFixed(activity);
            if (packageName != null) {
                new CustomTabsIntent.Builder()
                        .setToolbarColor(activity.getResources().getColor(R.color.colorPrimary))
                        .setShowTitle(true)
                        .build()
                        .launchUrl(activity, uri);
                return;
            }
        }

        // Intent.ACTION_VIEW
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        PackageManager pm = activity.getPackageManager();
        ResolveInfo ri = pm.resolveActivity(intent, 0);
        if (ri != null) {
            activity.startActivity(intent);
            return;
        }

        Toast.makeText(activity, R.string.error_cant_find_activity, Toast.LENGTH_SHORT).show();
    }
}